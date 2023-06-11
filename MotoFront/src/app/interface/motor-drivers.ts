export interface MotorDrivers {

  driver: {
    name: string;
    slug: string;
    code: string;
    picture: string;
    countryFlag: string;
  },
  team: {
    name: string;
    slug: string;
    code: string;
    picture: string;
    countryFlag: string;
  },
  season: {
    name: string;
    slug: string;
    year: number;
    endYear: number;
  },
  series: {
    name: string;
    shortName: string;
    code: string;
    slug: string;
    uuid: string;
    picture: string;
  },
  year: number;
  stats: {
    championshipRank: number;
    podiums: number;
    fastestLaps: number;
    avgGridPosition: number;
    wins: number;
  }
}
