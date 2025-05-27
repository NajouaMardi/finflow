import { Component, inject } from '@angular/core';
import {FormBuilder, Validators, FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatChip, MatChipListbox, MatChipSelectionChange, MatChipsModule} from "@angular/material/chips";
import {MatIcon} from "@angular/material/icon";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatStep, MatStepLabel, MatStepper, MatStepperNext, MatStepperPrevious} from "@angular/material/stepper";
import {MatButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {formatDate, NgForOf, NgIf} from "@angular/common";
import {MatCard} from "@angular/material/card";
import {User, UserService} from "../../../../services/services/user.service";
import {TokenService} from "../../../../services/token/token.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {RefreshService} from "../../../../services/services/refresh.service";

@Component({
  selector: 'app-set-up',
  templateUrl: './set-up.component.html',
  styleUrls: ['./set-up.component.css'],
  imports: [
    MatChip,
    MatIcon,
    MatFormField,
    MatStep,
    MatStepper,
    ReactiveFormsModule,
    MatStepperNext,
    MatButton,
    MatInput,
    MatStepLabel,
    MatLabel,
    MatMiniFabButton,
    NgForOf,
    MatStepperPrevious,
    MatChipsModule,
    MatIconButton,
    NgIf,
    MatCard,
  ],
  standalone: true
})
export class SetUpComponent {
  private _formBuilder = inject(FormBuilder);

  firstFormGroup = this._formBuilder.group({
    incomeCtrl: ['', [Validators.required, Validators.min(0)]],
  });

  secondFormGroup = this._formBuilder.group({
    categoryCtrl: ['', Validators.required],
  });

  budgetForm = this._formBuilder.group({});

  categories: string[] = [];

  addCategory() {
    // Defensive: check form validity
    if (this.secondFormGroup.invalid) return;

    // Safely get category value
    const categoryValue = this.secondFormGroup.get('categoryCtrl')?.value?.trim() ?? '';

    // Avoid duplicates and empty strings
    if (categoryValue && !this.categories.includes(categoryValue)) {
      this.categories.push(categoryValue);

      // Add new FormControl to budgetForm with index as key
      this.budgetForm.addControl(
        (this.categories.length - 1).toString(),
        new FormControl('', [Validators.required, Validators.min(0)])
      );

      // Clear category input for next entry
      this.secondFormGroup.reset();
    }
  }

  removeCategory(index: number) {
    // Remove category from array
    this.categories.splice(index, 1);

    // Remove control from budgetForm
    this.budgetForm.removeControl(index.toString());

    // Because keys are indices as strings, we need to shift keys after removal:
    const newBudgetGroup = this._formBuilder.group({});
    this.categories.forEach((_, i) => {
      const ctrlValue = this.budgetForm.get((i + 1).toString())?.value || '';
      newBudgetGroup.addControl(i.toString(), new FormControl(ctrlValue, [Validators.required, Validators.min(0)]));
    });
    this.budgetForm = newBudgetGroup;
  }


  finish() {
    if (this.budgetForm.invalid || this.firstFormGroup.invalid || !this.fullUser?.id) return;

    const incomeRaw = this.firstFormGroup.get('incomeCtrl')?.value;
    const income = Number(incomeRaw ?? 0);

    const month = formatDate(new Date(), 'yyyy-MM', 'en-US');

    const categories = this.categories.map((cat, i) => {
      const rawValue = this.budgetForm.get(i.toString())?.value;
      return {
        category: cat,
        budget: Number(rawValue ?? 0)
      };
    });

    const data: BudgetSubmission = {
      userId: this.fullUser.id,
      income,
      month,
      categories
    };

    this.http.post('http://localhost:8088/api/v1/categories/save', data, { responseType: 'text' })
      .subscribe({
        next: res => {
          console.log('Saved successfully!', res);
          this.refreshService.triggerRefresh();
          this.router.navigate(['/assistant']);
        },
        error: err => console.error('Save failed:', err)
      });
  }







  currentUserFromToken: any;
  fullUser: User | null = null;

  constructor(
    private tokenService: TokenService,
    private userService: UserService,
    private http: HttpClient,
    private router: Router,
    private refreshService: RefreshService
  ) {}

  ngOnInit(): void {
    this.currentUserFromToken = this.tokenService.getUserEmail();

    if (this.currentUserFromToken) {
      this.userService.getUserByEmail(this.currentUserFromToken)
        .subscribe({
          next: user => this.fullUser = user,
          error: err => console.error('User fetch error:', err)
        });

    }
  }
}



// budget.model.ts
export interface CategoryBudget {
  category: string;
  budget: number;
}

export interface BudgetSubmission {
  userId: number;
  income: number;
  month: string; // e.g., "2025-05"
  categories: CategoryBudget[];
}
