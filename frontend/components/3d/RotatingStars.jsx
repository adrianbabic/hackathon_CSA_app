import React from 'react'
import { useFrame } from '@react-three/fiber'
import { OrbitControls, Stars } from '@react-three/drei'

export default function RotatingStars() {
    const mymesh = React.useRef()

    useFrame(({clock}) => {
        mymesh.current.rotation.x = clock.getElapsedTime() * 0.03
        mymesh.current.rotation.y = clock.getElapsedTime() * 0.03
    })

    return (
        <mesh ref={mymesh} className="w-screen h-screen">
            <Stars />
            <OrbitControls />
        </mesh>
    )
}
