import {Component, OnInit} from '@angular/core';
import {Chart, ChartOptions, ChartType, Color, Colors} from "chart.js";
import _default from "chart.js/dist/plugins/plugin.legend";
import labels = _default.defaults.labels;
import {ChartData} from "chart.js/dist/types";
import {BaseChartDirective} from "ng2-charts";
import {HttpServiceService} from "../../../services/http-service.service";
import {MotorDriversData} from "../../../interface/motor-drivers-data";
import {MotorDrivers} from "../../../interface/motor-drivers";

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css']
})
export class LineChartComponent implements OnInit{

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
  fastestLaps: number[] = []
  constructor(private htpp: HttpServiceService) {
    Chart.register(Colors)
  }

  getMotoDriversData() {
    this.htpp.getMotoSportData().subscribe((data: MotorDriversData) => {
      this.dataMotoDrivers = data
      this.dataMotoDrivers.content.sort((a, b) => {return a.stats.championshipRank - b.stats.championshipRank})
      console.log(this.dataMotoDrivers)
      this.RenderChart(this.dataMotoDrivers)
    })
  }

  RenderChart(dataMotoDrivers: MotorDriversData){

    dataMotoDrivers.content.map(value => {this.labelsMoto.push(value.driver.name)})

    dataMotoDrivers.content.map(value => {this.dataMoto.push(value.stats.championshipRank)})

    dataMotoDrivers.content.map(value => {this.fastestLaps.push(value.stats.fastestLaps)})

    new Chart("lineChart", {
      type: 'line',
      data: {
        labels: this.labelsMoto,
        datasets: [{
          label: 'Championship place',
          data: this.dataMoto,
          borderWidth: 1
        },
          {
            label: 'Driver Fastest Laps',
            data: this.fastestLaps,
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

  }
}
