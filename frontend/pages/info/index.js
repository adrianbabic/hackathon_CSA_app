import React from 'react'
import Navbar from '@/components/layout/Navbar'

const Info = () => {
  return (
    <>
    <Navbar />
    <h1 className="text-4xl font-bold font-mono text-center my-10">
          INFORMATION
        </h1>
    <div className='p-4'>
      <p className='my-4 text-2xl'><a className='text-blue-500' href="/dashboard">Dashboard</a> - Overview of core features on a website.</p>
      <p className='my-4 text-2xl'><a className='text-blue-500' href="/records">Record</a> - Records of previous threats and what happened on your computer.</p>
      <p className='my-4 text-2xl'><a className='text-blue-500' href="/scan">Scan</a> - Scan your computer to get information about possible cyber security issues you might have.</p>
      <p className='my-4 text-2xl'><a className='text-blue-500' href="/alerts">Alerts</a> -  Information that is critical that you have to worry about right away.</p>
      <p className='my-4 text-2xl'><a className='text-blue-500' href="/reports">Reports</a> - Detailed view of your past activities, you can view stats and graphs.</p>
      <p className='my-4 text-2xl'><a className='text-blue-500' href="/system">System</a> - Overview of Admins and Users and you can change them and remove user rights..</p>
      

    </div>
    </>
    
  )
}

export default Info