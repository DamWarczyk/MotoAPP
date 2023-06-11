import {Component, OnInit} from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import {Chart, ChartConfiguration, ChartData, ChartEvent, ChartOptions, ChartType, Colors} from 'chart.js';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import {MotorDriversData} from "../../../interface/motor-drivers-data";
import {HttpServiceService} from "../../../services/http-service.service";

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit{
  dataMotoDrivers: MotorDriversData = {
    content: [],
    number: 1,
    numberOfElements: 2,
    size: 3,
    totalElements: 4,
    totalPages: 3,
  };

  dataMoto: number[] = []
  labelsMoto: string[] = []
  driversMotoWinsData: number[] = []
  constructor(private htpp: HttpServiceService) {
    Chart.register(Colors)
  }

  getMotoDriversData() {
    this.htpp.getMotoSportData().subscribe((data: MotorDriversData) => {
      this.dataMotoDrivers = data
      this.dataMotoDrivers.content.sort((a, b) => {return a.stats.championshipRank - b.stats.championshipRank})
      this.RenderChart(this.dataMotoDrivers)
    })
  }

  RenderChart(dataMotoDrivers: MotorDriversData){

    dataMotoDrivers.content.map(value => {this.labelsMoto.push(value.driver.name)})

    dataMotoDrivers.content.map(value => {this.dataMoto.push(value.stats.championshipRank)})

    dataMotoDrivers.content.map(value => {this.driversMotoWinsData.push(value.stats.wins)})

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
  }}
