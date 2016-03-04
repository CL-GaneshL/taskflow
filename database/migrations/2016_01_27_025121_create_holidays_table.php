<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateHolidaysTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('holidays', function (Blueprint $table) {

            // primary key ------------------
            $table->increments('id');
            // ------------------------------

            $table->string('title');
            $table->integer('employee_id')->unsigned();
            $table->date('start_date');
            $table->date('end_date');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('holidays');
    }

}
