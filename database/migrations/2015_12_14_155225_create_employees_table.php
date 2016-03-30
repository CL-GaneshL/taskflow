<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateEmployeesTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {

        Schema::create('employees', function (Blueprint $table) {

            // primary key ------------------
            $table->increments('id');
            // ------------------------------

            $table->string('employeeId')->unique();
            $table->string('firstName')->nullable();
            $table->string('lastName')->nullable();
            $table->string('fullName')->nullable();
            $table->string('avatar')->default('../../img/caratlane/avatar-150.png');
            $table->string('location')->nullable();
            $table->string('email')->nullable();
            $table->string('phone')->nullable();
            $table->float('productivity')->default(8.0)->unsigned();
            $table->boolean('isProjectManager')->default(0);
            $table->boolean('isTeamLeader')->default(0);
            $table->enum('employementType', array('Intern', 'FTE'));
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('employees');
    }

}
