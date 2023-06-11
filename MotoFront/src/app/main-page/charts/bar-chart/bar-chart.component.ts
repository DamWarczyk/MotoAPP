import {Component, OnInit} from '@angular/core';
import {BaseChartDirective} from 'ng2-charts';
import {
  Chart,
  ChartConfiguration,
  ChartData,
  ChartDataset,
  ChartEvent,
  ChartOptions,
  ChartType,
  Colors
} from 'chart.js';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import {MotorDriversData} from "../../../interface/motor-drivers-data";
import {HttpServiceService} from "../../../services/http-service.service";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {
  dataMotoDrivers: MotorDriversData = {
    content: [],
    number: 1,
    numberOfElements: 2,
    size: 3,
    totalElements: 4,
    totalPages: 3,
  };

  year: number = 2021

  dataMoto: number[] = []
  labelsMoto: string[] = []
  driversMotoWinsData: number[] = []

  constructor(private http: HttpServiceService) {
  }

  getMotoDriversData() {
    this.http.getMotoSportData().subscribe((data: MotorDriversData) => {
      this.dataMotoDrivers = data
      this.dataMotoDrivers.content.sort((a, b) => {
        return a.stats.championshipRank - b.stats.championshipRank
      })
      this.RenderChart(this.dataMotoDrivers)
    })
  }

  RenderChart(dataMotoDrivers: MotorDriversData) {

    this.labelsMoto = []
    this.dataMoto = []
    this.driversMotoWinsData = []

    dataMotoDrivers.content.map(value => {
      this.labelsMoto.push(value.driver.name)
    })

    dataMotoDrivers.content.map(value => {
      this.dataMoto.push(value.stats.championshipRank)
    })

    dataMotoDrivers.content.map(value => {
      this.driversMotoWinsData.push(value.stats.wins)
    })


    new Chart("barChart", {
      type: 'bar',
      data: {
        labels: this.labelsMoto,
        datasets: [
          {
            label: 'Driver Wins',
            data: this.driversMotoWinsData,
            borderWidth: 1
          }
        ]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  ngOnInit(): void {
    this.getMotoDriversData()
  }

  onClick() {
    this.http.getMotoSportByYear(this.year).subscribe(
      (data) => {
        this.dataMotoDrivers.content.sort((a, b) => {
          return a.stats.wins - b.stats.wins
        })
        Chart.getChart("barChart")?.destroy()
        this.RenderChart(data)
      })
  }

  inputEvent(event: any) {
    this.year = event.target.value
  }


}
