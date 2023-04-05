import React from 'react'
import {Canvas, useFrame} from '@react-three/fiber'

import RotatingStars from "@/components/3d/RotatingStars"
import LandingPageNavbar from "@/components/layout/LandingPageNavbar"

export default function Home() {
  return (
    <>
    <LandingPageNavbar />
    <div className="w-screen h-screen">
        <Canvas>
            <RotatingStars/>
        </Canvas>
    </div>
    <div className='absolute inset-y-2/4 text-center flex w-screen' style={{ left: "14%"}}>
        <h1 className="text-6xl text-center absoulte">Welcome to our cybersecurity landing page!</h1>
    </div>
    </>
)
}
