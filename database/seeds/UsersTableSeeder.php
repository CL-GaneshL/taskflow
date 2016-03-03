<?php

use App\Employee;
use App\User;
use Illuminate\Database\Seeder;

class UsersTableSeeder extends Seeder {

    public function run() {

        DB::table('users')->insert([
            [
                'employeeId' => "0",
                'userName' => "admin",
//                'userName' => "TaskFlow Admin",
//                'password' => Hash::make('Bharata'),
                'password' => Hash::make('secret'),
                'role_id' => "1", // ADMIN_ROLE
            ]
        ]);

        $employees = Employee::all();
        foreach ($employees as $employee) {

            $role = null;

            if ($employee->id == 1) {
                // project manager
                $role = 2;
            } else if ($employee->id == 2 || $employee->id == 3) {
                // team leads
                $role = 3;
            } else {
                $role = 4;
            }

            $auth = [
                'employeeId' => $employee->id,
                'username' => $employee->employeeId,
                'role_id' => $role
            ];

            User::create($auth);
        }
    }

}
