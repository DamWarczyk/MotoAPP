import _default from "chart.js/dist/plugins/plugin.tooltip";
import numbers = _default.defaults.animations.numbers;
import {MotorDrivers} from "./motor-drivers";

export interface MotorDriversData {

  size: number;
  numberOfElements: number;
  totalElements: number;
  number: number;
  totalPages: number;
  content: MotorDrivers[];
}
