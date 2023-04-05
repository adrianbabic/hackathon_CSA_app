import React from 'react'
import Link from 'next/link'
import {useRouter} from 'next/router';

const Navbar = () => {
    const router = useRouter()
    const handleSignOut = () => {
        localStorage.removeItem('token')
        router.push('/')
    }
    
  return (
    <>
    
    <div className='flex items-center justify-between px-8 py-4 dark:bg-gray-800 dark:border-gray-700 font-sans rounded'>
        <div>
            <h1 className='text-4xl font-bold font-mono'>CyberSecurity</h1>
        </div>
        <div className=''>
        <ul className='flex items-center gap-5 text-lg '>
      <li>
        <Link href="/dashboard" className={`hover:border-b-2 ${router.pathname === "/dashboard" ? "font-bold" : ""} `}>Dashboard</Link>
      </li>
      <li>
        <Link href="/records" className={`hover:border-b-2 ${router.pathname === "/records" ? "font-bold" : ""}`}>Records</Link>
      </li>
      <li>
        <Link href="/scan" className={`hover:border-b-2 ${router.pathname === "/scan" ? "font-bold" : ""}`}>Scan</Link>
      </li>
      <li>
        <Link href="/alerts" className={`hover:border-b-2 ${router.pathname === "/alerts" ? "font-bold" : ""}`}>Alerts</Link>
      </li>
      
      <li>
        <Link href="/system" className={`hover:border-b-2 ${router.pathname === "/system" ? "font-bold" : ""}`}>System</Link>
      </li>
      <li>
        <Link href="/info" className={`hover:border-b-2 ${router.pathname === "/info" ? "font-bold" : ""}`}>Info</Link>
      </li>
      <li>
        <button onClick={handleSignOut} className='hover:border-b-2'>Sign Out</button>
      </li>
    </ul>
        </div>
    </div>
    </>
  )
}

export default Navbar