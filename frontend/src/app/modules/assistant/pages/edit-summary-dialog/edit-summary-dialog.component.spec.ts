import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSummaryDialogComponent } from './edit-summary-dialog.component';

describe('EditSummaryDialogComponent', () => {
  let component: EditSummaryDialogComponent;
  let fixture: ComponentFixture<EditSummaryDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditSummaryDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditSummaryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
