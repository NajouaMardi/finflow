import { Component, Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {MatButton, MatButtonModule} from '@angular/material/button';
import {MatFormField, MatInput, MatInputModule} from '@angular/material/input';
import {MatLabel} from "@angular/material/form-field";


@Component({
  selector: 'app-edit-summary-dialog',
  standalone: true,
  imports: [
    MatFormField,
    ReactiveFormsModule,
    MatDialogTitle,
    MatButton,
    MatInput,
    MatLabel
  ],
  templateUrl: './edit-summary-dialog.component.html',
  styleUrl: './edit-summary-dialog.component.css'
})
export class EditSummaryDialogComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EditSummaryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.form = this.fb.group({
      actualAmount: [data.actualAmount, [Validators.required, Validators.min(0)]],
    });
  }

  onSave(): void {
    if (this.form.valid) {
      this.dialogRef.close({ ...this.data, actualAmount: this.form.value.actualAmount });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }





}
