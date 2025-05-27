import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CategorySummaryService {
  private apiUrl = 'http://localhost:8088/api/v1';

  constructor(private http: HttpClient) {}

  getSummaries(userId: number): Observable<CategorySummary[]> {
    return this.http.get<CategorySummary[]>(`${this.apiUrl}/categories/summaries/${userId}`);
  }




  deleteCategoryEntry(entry: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/categories/deleteSummary/categorysummary`, {
      params: {
        userId: entry.userId,
        category: entry.category,
        month: entry.month
      },responseType: 'text'
    });
  }



  updateActualAmount(dto: SpendingUpdateDTO): Observable<CategorySummary> {
    console.log("dto in service",dto)
    return this.http.post<CategorySummary>(`${this.apiUrl}/spendings/update`, dto);
  }



  /*updateActualAmount(summary: CategorySummary): Observable<any> {
    return this.http.put(`${this.apiUrl}/spendings/update`, summary);
  }*/



  addCategorySummary(dto: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/categories/addCategorySummary`, dto, { responseType: 'text' });
  }


}
export interface CategorySummary {
  category: string;
  month: string;
  plannedAmount: number;
  actualAmount: number;
}


export interface SpendingUpdateDTO {
  userId: number;
  categoryName: string;
  month: string;
  actualAmount: number;
}

