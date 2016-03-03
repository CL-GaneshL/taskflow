<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('users', function (Blueprint $table) {

            // primary key ------------------
            $table->increments('id');
            // ------------------------------

            $table->integer('employeeId')->unsigned()->unique();
            $table->integer('role_id')->unsigned();
            $table->string('username');
            $table->string('password', 60)->default(Hash::make('secret'));
            $table->rememberToken();

            // ------------------------------------------
            // - Laravel fields
            // ------------------------------------------
            $table->timestamps();

            // ------------------------------------------
            // - indexes
            // ------------------------------------------
            $table->unique(array('username', 'password'));
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('users');
    }

}
