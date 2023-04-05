import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Filler,
    Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(    
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Filler,
    Legend
);

const options = {
    responsive: true,
  plugins: {
    legend: {
      position: 'top',
    },
    title: {
      display: true,
      text: 'Threats',
    },
  },
}

const labels = ["January", "February", "March", "April", "May", "June", "July"];
const data = {
    labels: labels,
    datasets: [
        {
        fill: true,
        label: 'Severities',
        data: [10, 30, 46, 32, 33, 40, 38],
        borderColor: 'rgb(53, 162, 235)',
        backgroundColor: 'rgba(53, 162, 235, 0.5)',
        },
    ],
};



export default function Graph() {
    return (
        <Line data={data} options={options} />
    )
}