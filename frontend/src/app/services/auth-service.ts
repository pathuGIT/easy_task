import { Injectable } from '@angular/core';
import { environment } from '../../environement/environemnt';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthResponse } from '../interfaces/authResponse';
import { ProfileResponse } from '../interfaces/profileResponse';
import { RegResponse } from '../interfaces/RegResponse';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = environment.api;
  private accessTokenKey = 'access_token';
  private refreshTokenKey = 'refresh_token';

  private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasTokens());
  isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor(private http: HttpClient) {}

  register(username: string, password: string): Observable<RegResponse> {
    return this.http
      .post<RegResponse>(`${this.apiUrl}/auth/register`, { username, password });
  }

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/auth/login`, { username, password })
      .pipe(
        tap(res => {
          this.storeTokens(res.data.active_token, res.data.refresh_token);
          this.isLoggedInSubject.next(true);
        })
      );
  }

  refreshToken(): Observable<AuthResponse> {
    const refreshToken = this.getRefreshToken();
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/auth/refresh`, { refreshToken })
      .pipe(
        tap(res => {
          this.storeTokens(res.data.active_token, res.data.refresh_token);
        })
      );
  }

  isLoggedIn(): boolean {
    return this.getAccessToken() !== null;
  }
  
  profile(): Observable<ProfileResponse>{
    return this.http
      .get<ProfileResponse>(`${this.apiUrl}/user/`);
  }

  logout(): void {
    this.clearTokens();
    this.isLoggedInSubject.next(false);
  }

  getAccessToken(): string | null {
    return localStorage.getItem(this.accessTokenKey);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem(this.refreshTokenKey);
  }

  private storeTokens(access: string, refresh: string) {
    localStorage.setItem(this.accessTokenKey, access);
    localStorage.setItem(this.refreshTokenKey, refresh);
  }

  private clearTokens() {
    localStorage.removeItem(this.accessTokenKey);
    localStorage.removeItem(this.refreshTokenKey);
  }

  private hasTokens(): boolean {
    return !!this.getAccessToken() && !!this.getRefreshToken();
  } 
}
