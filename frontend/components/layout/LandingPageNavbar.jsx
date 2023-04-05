export default function LandingPageNavbar() {
    // make navigation bar
    return (
        <nav className="bg-transparent border-dark-200 px-2 sm:px-4 py-2.5 rounded absolute w-screen" style={{zIndex: "1"}}>
            <ul className="flex flex-row justify-between">
                <li><a href="/">Home</a></li>
                <li><a href="/login">Login</a></li>
            </ul>
	    </nav>
    )
}
