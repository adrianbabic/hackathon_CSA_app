import Alerts from "./Alerts"
import ChartLayout from "./ChartLayout"
import Records from "./Records"
import UserInfo from "./UserInfo"

export default function Dashboard() {
    return (
        <div className="flex flex-row gap-4 w-screen h-screen justify-end p-4">
            <div className="flex flex-col w-1/2">
                <Alerts className="mb-8"/>
                <UserInfo />
            </div>
            <div className="flex flex-col w-1/2">
                <ChartLayout />
                <Records />
            </div>
        </div>
    )
}