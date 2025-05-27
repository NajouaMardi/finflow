import {Component, ViewChild} from '@angular/core';

import {TokenService} from "../../../../services/token/token.service";
import {NgIf} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {AddCategoryDialogComponent} from "../add-category-dialog/add-category-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {CategorySummaryService} from "../../../../services/services/category-summary.service";
import {MatFormField, MatFormFieldModule, MatHint, MatLabel} from "@angular/material/form-field";
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInput, MatInputModule} from "@angular/material/input";
import {
  MatDatepicker,
  MatDatepickerInput,
  MatDatepickerModule,
  MatDatepickerToggle
} from "@angular/material/datepicker";
import {DateAdapter, MatNativeDateModule} from "@angular/material/core";
import {CategorySummaryComponent} from "../category-summary/category-summary.component";
import {BudgetChartComponent} from "../budget-chart/budget-chart.component";
import {User, UserService} from "../../../../services/services/user.service";
import {RefreshService} from "../../../../services/services/refresh.service";

@Component({
  selector: 'app-user-home',
  standalone: true,
  imports: [
    CategorySummaryComponent,
    BudgetChartComponent,
    NgIf,
    MatIcon,
    MatMiniFabButton,
    MatFormField,
    FormsModule,
    MatIconButton,
    MatInput,
    MatLabel,
    MatDatepickerInput,
    ReactiveFormsModule,
    MatDatepicker,
    MatDatepickerToggle,
    MatHint,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './user-home.component.html',
  styleUrl: './user-home.component.css'
})
export class UserHomeComponent {
  currentUserFromToken: any;
  currentUserFullName : any;
  fullUser!: User | null;


  monthlyIncome = 0;
  monthlyBudget = 0;
  savedAmount = 0;


  currentMonth!: string;

  constructor(
    private tokenService: TokenService,
    private userService: UserService,
    private dialog: MatDialog, private summaryService: CategorySummaryService,
    private dateAdapter: DateAdapter<Date>,
    private refreshService: RefreshService
  ) {this.dateAdapter.setLocale('en-GB'); }

  ngOnInit(): void {
    this.currentUserFromToken = this.tokenService.getUserEmail();
    this.currentUserFullName = this.tokenService.getFullName();

    const now = new Date();
    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0');
    this.currentMonth = `${year}-${month}`;

    if (this.currentUserFromToken) {
      this.userService.getUserByEmail(this.currentUserFromToken)
        .subscribe({
          next: user => {
            this.fullUser = user;
            console.log("is it teh user", this.fullUser)
            this.loadMonthlySummary(); // <-- call AFTER user is loaded
          },
          error: err => console.error('User fetch error:', err)
        });
    }

    this.refreshService.refresh$.subscribe(() => {
      this.loadMonthlySummary();       // refresh the data
      this.onDataUpdated();            // refresh the chart if needed
    });}




  loadMonthlySummary() {
    if (!this.fullUser?.id || !this.currentMonth) return;
    this.userService.getMonthlySummary(this.fullUser.id, this.currentMonth).subscribe(summary => {
      this.monthlyIncome = summary.totalIncome || 0;
      this.monthlyBudget = summary.totalBudget || 0;
      this.savedAmount = (summary.totalIncome || 0) - (summary.totalSpending || 0);
    });
  }

  openAddCategoryDialog() {
    if(!this.fullUser?.id) return;
    const dialogRef = this.dialog.open(AddCategoryDialogComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // result contains { category, plannedAmount, actualAmount }
        // Create the DTO for saving spending, current month, userId
        const currentMonth = new Date().toISOString().slice(0, 7); // "YYYY-MM"
        const dto = {
          userId: this.fullUser?.id,
            category: result.category,
            month: currentMonth,
            plannedAmount: result.plannedAmount,
            actualAmount: result.actualAmount

        };

        // Call your service method to save the new spending
        this.summaryService.addCategorySummary(dto).subscribe(() => {
          // Refresh the category summary list after adding
          // e.g., you might emit an event or call a method on the child component to reload data
          this.loadMonthlySummary();

          console.log('Category summary added successfully');
        }, error => {
          console.error('Error adding category summary:', error);
        });
      }
    });
  }


  @ViewChild('budgetChart') budgetChartComponent!: BudgetChartComponent;

  searchTerm: string = '';


  onDataUpdated() {
    this.budgetChartComponent.loadChartData();
    this.loadMonthlySummary();
  }





  date = new FormControl(new Date()); // holds the selected date (month + year)



  setMonthAndYear(normalizedMonthAndYear: Date, datepicker: any): void {
    const ctrlValue = this.date.value || new Date();
    ctrlValue.setMonth(normalizedMonthAndYear.getMonth());
    ctrlValue.setFullYear(normalizedMonthAndYear.getFullYear());
    this.date.setValue(new Date(ctrlValue));
    datepicker.close();
  }

  chosenYearHandler(normalizedYear: Date): void {
    const ctrlValue = this.date.value || new Date();
    ctrlValue.setFullYear(normalizedYear.getFullYear());
    this.date.setValue(new Date(ctrlValue));
  }


}
