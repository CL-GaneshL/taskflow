<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateProjectsTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('projects', function (Blueprint $table) {

            // primary key ------------------
            $table->increments('id');
            // ------------------------------

            $table->string('reference');
            $table->integer('template_id')->unsigned();
            $table->integer('nb_products')->unsigned();
            $table->tinyInteger('priority')->unsigned();
            $table->date('start_date');
            $table->boolean('open')->default(true);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('projects');
    }

}
