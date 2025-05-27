import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ChartConfiguration, ChartOptions} from "chart.js";
import {BudgetChartService} from "../../../../services/services/budget-chart.service";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {BaseChartDirective} from "ng2-charts";

@Component({
  selector: 'app-budget-chart',
  standalone: true,
  imports: [
    NgIf,
    FormsModule,
    NgForOf,
    BaseChartDirective
  ],
  templateUrl: './budget-chart.component.html',
  styleUrl: './budget-chart.component.css'
})

export class BudgetChartComponent implements OnInit {
  @Input() userId!: number;  // <--- input property

  selectedYear = new Date().getFullYear();
  chartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      { label: 'Budget', data: [], borderColor: '#5A9BD5', fill: false, tension: 0.3 },
      { label: 'Spending', data: [], borderColor: '#E57373', fill: false, tension: 0.3 }
    ]
  };

  constructor(private budgetChartService: BudgetChartService) {}

  public lineChartLegend = true;
  ngOnInit() {
    if (this.userId) {
      this.loadChartData();
    }
  }


  chartOptions: ChartOptions<'line'> = {
    responsive: false,
    scales: {
      x: { title: { display: true, text: 'Month' } },
      y: { title: { display: true, text: 'Amount (MAD)' }, beginAtZero: true }
    },
    plugins: {
      legend: { position: 'top' }
    }
  };

  // Provide year options (e.g. last 5 years)
  getYearOptions(): number[] {
    const currentYear = new Date().getFullYear();
    return Array.from({ length: 5 }, (_, i) => currentYear - i);
  }

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  loadChartData(): void {
    if (!this.userId) return; // safety check

    this.budgetChartService.getMonthlyData(this.userId, this.selectedYear).subscribe(data => {
      console.log("data received", data)
      this.chartData.labels = data.map(d => d.month);
      console.log("labels", this.chartData.labels)
      this.chartData.datasets[0].data = data.map(d => d.budget);
      console.log("budgets", this.chartData.datasets[0].data)
      this.chartData.datasets[1].data = data.map(d => d.spending);
      console.log("spendings", this.chartData.datasets[1].data)

      //this.loadChartData();
      // Trigger chart update
      this.chart?.update();
    });
  }


}
