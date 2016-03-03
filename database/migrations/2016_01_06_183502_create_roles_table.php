<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRolesTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('roles', function (Blueprint $table) {

            // primary key ------------------
            $table->increments('id');
            // ------------------------------

            $table->enum('role_name', array(
                'ADMIN_ROLE',
                'PROJECT_MANAGER_ROLE',
                'TEAM_LEAD_ROLE',
                'EMPLOYEE_ROLE'
                    )
            );

            $table->string('designation');

            // indexes ----------------------
            $table->unique('role_name');
            // ------------------------------
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('roles');
    }

}
