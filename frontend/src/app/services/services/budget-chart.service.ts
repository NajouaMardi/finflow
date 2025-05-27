import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { forkJoin, map, Observable } from 'rxjs';

export interface MonthlyBudgetData {
  month: string;       // e.g. "January"
  budget: number;      // plannedAmount
  spending: number;    // actualAmount
}

@Injectable({
  providedIn: 'root'
})
export class BudgetChartService {
  private baseUrl = 'http://localhost:8088/api/v1';

  constructor(private http: HttpClient) {}

  getMonthlyData(userId: number, year: number): Observable<MonthlyBudgetData[]> {
    console.log("the year is received", year)
    const budgets$ = this.http.get<any[]>(`${this.baseUrl}/monthlybudgets/user/${userId}`);
    const spendings$ = this.http.get<any[]>(`${this.baseUrl}/spendings/user/${userId}`);
    return forkJoin([budgets$, spendings$]).pipe(
      map(([budgets, spendings]) => {
        // Filter by year
        const filteredBudgets = budgets.filter(b => b.month.startsWith(year.toString()));
        console.log("dilteredBudgets", filteredBudgets)
        const filteredSpendings = spendings.filter(s => s.month.startsWith(year.toString()));
        console.log("dilteredSpendings", filteredSpendings)

        // Create a map for quick lookup
        /*const spendingMap = new Map<string, number>();
        for (const s of filteredSpendings) {
          spendingMap.set(s.month, (spendingMap.get(s.month) || 0) + s.actualAmount);
        }
        console.log("spendingMap", spendingMap)

        const result: MonthlyBudgetData[] = [];

        const budgetMap = new Map<string, number>();
        for (const b of filteredBudgets) {
          const ym = b.month; // e.g. "2025-01"
          console.log("ym", ym)
          const monthName = this.getMonthName(ym);
          console.log("monthName", monthName)
          const budget = b.plannedAmount;
          console.log("plannedAmount",budget );
          const spending = spendingMap.get(ym) || 0;
          console.log("spending", spending);

          result.push({ month: monthName, budget, spending });
          console.log("result", result)
        }



        return result.sort((a, b) =>
          new Date(`${year}-${this.monthNumber(a.month)}-01`).getTime()
          - new Date(`${year}-${this.monthNumber(b.month)}-01`).getTime()
        );*/
        const spendingMap = new Map<string, number>();
        for (const s of filteredSpendings) {
          spendingMap.set(s.month, (spendingMap.get(s.month) || 0) + s.actualAmount);
        }
        console.log("spendingMap",spendingMap)

        const budgetMap = new Map<string, number>();
        for (const b of filteredBudgets) {
          budgetMap.set(b.month, (budgetMap.get(b.month) || 0) + b.plannedAmount);
        }
        console.log("budgetMap",budgetMap)

// Combine months from both maps (to cover all months present in either map)
        const allMonths = new Set<string>([...spendingMap.keys(), ...budgetMap.keys()]);

        console.log("allMonths",allMonths)
        const result: MonthlyBudgetData[] = [];

        for (const ym of allMonths) {
          const budget = budgetMap.get(ym) || 0;
          const spending = spendingMap.get(ym) || 0;
          const monthName = this.getMonthName(ym); // e.g., "2023-02" -> "February"


          result.push({
            month: monthName,
            budget,
            spending
          });
        }
        console.log("result",result)

        // Sort by actual calendar order
        const monthOrder = [
          "January", "February", "March", "April", "May", "June",
          "July", "August", "September", "October", "November", "December"
        ];

        console.log("result sorted",result.sort((a, b) =>
          monthOrder.indexOf(a.month) - monthOrder.indexOf(b.month)
        ))
        return result.sort((a, b) =>
          monthOrder.indexOf(a.month) - monthOrder.indexOf(b.month)
        );


      })
    );
  }

  private getMonthName(ym: string): string {
    const month = parseInt(ym.split('-')[1], 10);
    return new Date(2000, month - 1).toLocaleString('en-US', { month: 'long' });
  }

  private monthNumber(name: string): number {
    return new Date(Date.parse(name +" 1, 2020")).getMonth() + 1;
  }
}
