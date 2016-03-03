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
            $table->boolean('start_morning_shift');
            $table->boolean('start_afternoon_shift');

            $table->date('end_date');
            $table->boolean('end_morning_shift');
            $table->boolean('end_afternoon_shift');
             
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
