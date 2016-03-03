<?php

use Illuminate\Database\Seeder;

class TeamsHaveEmployeesTableSeeder extends Seeder {

    public function run() {
        DB::table('teams_have_employees')->insert(
                [
                    // ---------------------------------------
                    // - Team	=> Imaging-Clean Up
                    // ---------------------------------------
                    [
                        'teamId' => 1,
                        'employeeId' => 1       // CL0138	Appolin A
                    ],
                    [
                        'teamId' => 1,
                        'employeeId' => 10       // CL0285	Arun J
                    ],
                    [
                        'teamId' => 1,
                        'employeeId' => 9       // CL0284	Muhammed Sabique
                    ],
                    [
                        'teamId' => 1,
                        'employeeId' => 7       // CL0223	Mukesh Raj
                    ],
                    [
                        'teamId' => 1,
                        'employeeId' => 8       // CL0286	Linto Joy
                    ],
                    // ---------------------------------------
                    // - Team	=> Imaging - Marketing video
                    // ---------------------------------------
                    [
                        'teamId' => 2,
                        'employeeId' => 3       // CL0014	Prasanna T
                    ],
                    [
                        'teamId' => 2,
                        'employeeId' => 4       // CL0154	Venkatesan R
                    ],
                    // ---------------------------------------
                    // - Team	=> Imaging - 3d model
                    // ---------------------------------------
                    [
                        'teamId' => 3,
                        'employeeId' => 4       // CL0154	Venkatesan R
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 6       // CL0216	Gokul Anand Raj
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 11       // CLI070	Selva Kumar S
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 12       // CLI076	Venkatesh R
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 13       // CLI082	Nagarajan GS
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 14       // CLI074	Balamurugan
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 15       // CLI075	Prince Jeyaseelan
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 16       // CLI077	Prabhu K
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 17       // CLI078	Saisudarsn V
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 18       // CLI079	Sathishkumar S
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 19       // CLI080	Richard Benny G
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 20       // CLI081	Vinothkumar J
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 21       // CLI083	Abin Babu E
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 22       // CLI084	Elavarasan S
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 23       // CLI085	Gowtham D
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 24       // CLI086	Karthik C
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 25       // CLI087	Maheshwaran M
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 26       // CLI088	Suresh L
                    ],
                    [
                        'teamId' => 3,
                        'employeeId' => 28       // CLI090	Shanmuga M
                    ],
                    // ---------------------------------------
                    // - Team	=> Imaging - VTO
                    // ---------------------------------------
                    [
                        'teamId' => 4,
                        'employeeId' => 6       // CL0216	Gokul Anand Raj
                    ],
                    // ---------------------------------------
                    // - Team	=> Imaging - Photoshoot
                    // ---------------------------------------
                    [
                        'teamId' => 5,
                        'employeeId' => 5       // CL0171	Manoj Kumar
                    ],
                    [
                        'teamId' => 5,
                        'employeeId' => 27       // CLI089	Suriya Kumar.K
                    ]
                ]
        );
    }

}
