import {Component, ViewChild, AfterViewInit, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import {
  MatCell,
  MatCellDef, MatColumnDef, MatHeaderCell,
  MatHeaderCellDef, MatHeaderRow,
  MatHeaderRowDef, MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {CategorySummary, CategorySummaryService} from "../../../../services/services/category-summary.service";
import {NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../confirm-dialog/confirm-dialog.component";
import {EditSummaryDialogComponent} from "../edit-summary-dialog/edit-summary-dialog.component";
import {RefreshService} from "../../../../services/services/refresh.service";

@Component({
  selector: 'app-category-summary',
  templateUrl: './category-summary.component.html',
  standalone: true,
  imports: [
    MatPaginator,
    MatRowDef,
    MatHeaderRowDef,
    MatCellDef,
    MatHeaderCellDef,
    MatTable,
    NgIf,
    MatHeaderCell,
    MatColumnDef,
    MatCell,
    MatButton,
    MatHeaderRow,
    MatRow
  ],
  styleUrls: ['./category-summary.component.css']
})
export class CategorySummaryComponent implements OnInit, OnInit, AfterViewInit {
  @Input() userId!: number;
  @Input() searchTerm!: string;
  displayedColumns: string[] = ['category', 'month', 'plannedAmount', 'actualAmount', 'actions'];
  dataSource = new MatTableDataSource<CategorySummary>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private dialog: MatDialog,private summaryService: CategorySummaryService,private refreshService: RefreshService) {}
  ngOnInit(): void {
    // Initial data load when the component initializes
    this.loadSummaries();
  }

  ngAfterViewInit(): void {
    // Set the paginator for the dataSource once the view is initialized
    // This ensures the paginator is correctly linked to the dataSource
    this.dataSource.paginator = this.paginator;
    console.log("Paginator linked to dataSource:", this.dataSource.paginator);
  }

  /**
   * Loads category summaries for the current user and updates the table's data source.
   */
  private loadSummaries(): void {
    if (this.userId) {
      this.summaryService.getSummaries(this.userId).subscribe(data => {
        console.log("Data received from summaries:", data);
        // Update the data property of the existing dataSource instance
        this.dataSource.data = data;
        console.log("DataSource data updated.");
      }, error => {
        console.error("Error loading summaries:", error);
        // Handle error, e.g., show a message to the user
      });
    } else {
      console.warn("userId is not provided. Cannot load summaries.");
    }
  }

  /**
   * Handles the edit action for a category summary row.
   * @param row The CategorySummary object to be edited.
   */
  edit(row: CategorySummary): void {
    const dialogRef = this.dialog.open(EditSummaryDialogComponent, {
      width: '400px',
      data: { ...row }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Build the SpendingUpdateDTO from dialog result + userId from input
        const dto = {
          userId: this.userId,          // from component input
          categoryName: result.category,    // from row data
          month: result.month,          // from row data
          actualAmount: result.actualAmount // updated value from dialog
        };
        console.log("dto",dto)

        // Call your service method that matches your backend update endpoint
        this.summaryService.updateActualAmount(dto).subscribe(() => {
          this.loadSummaries(); // Refresh table after update

          this.refreshService.triggerRefresh()
          this.dataUpdated.emit();
        }, error => {
          console.error('Failed to update spending:', error);
        });
      }
    });
  }



  /**
   * Handles the delete action for a category summary row.
   * Opens a confirmation dialog before proceeding with deletion.
   * @param row The CategorySummary object to be deleted.
   */
  delete(row: CategorySummary): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '300px',
      data: 'Are you sure you want to delete this entry?',
    });






    dialogRef.afterClosed().subscribe(result => {
      if (result === true) { // If the user confirms deletion
        console.log('User confirmed deletion for:', row);
        this.summaryService.deleteCategoryEntry({
            userId: this.userId,
            category: row.category,
            month: row.month

        }).subscribe(() => {
          console.log('Entry deleted successfully. Refreshing table...');
          // After successful deletion, reload the summaries to update the table
          this.loadSummaries();
          this.refreshService.triggerRefresh();
        }, error => {
          console.error("Error deleting entry:", error);
          // Handle error, e.g., show an error message to the user
        });
      } else {
        console.log('Deletion cancelled by user.');
      }
    });
  }


  @Output() dataUpdated = new EventEmitter<void>();
}
