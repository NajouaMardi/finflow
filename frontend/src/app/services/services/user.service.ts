import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface User {
  id: number;
  fullname: string;
  email: string;
  // any other fields your User entity has
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8088/api/v1'; // change to your backend URL

  constructor(private http: HttpClient) {}

  getUserByEmail(email: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/users/email/${email}`);
  }


  getMonthlySummary(userId: number, month: string): Observable<MonthlySummary> {
    return this.http.get<MonthlySummary>(`${this.apiUrl}/incomes/current/${userId}/${month}`);
  }
}
export interface MonthlySummary {
  totalIncome: number;
  totalBudget: number;
  totalSpending: number;
  savedAmount?: number; // computed client-side if needed
}
