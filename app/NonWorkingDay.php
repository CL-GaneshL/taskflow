<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class NonWorkingDay extends Model {

    // ----------------------------------
    // - fillable attributs
    // ----------------------------------
    protected $fillable = ['id', 'title', 'type', 'date', 'morning_shift', 'afternoon_shift'];
    // ----------------------------------
    // - remove timestamps
    // ----------------------------------
    public $timestamps = false;

    // ----------------------------------
    // - getters
    // ----------------------------------
    function getId() {
        return $this->fillable->id;
    }

    function getTitle() {
        return $this->fillable->title;
    }

    function getType() {
        return $this->fillable->type;
    }

    function getDate() {
        return $this->fillable->date;
    }

    function getMorningShift() {
        return $this->fillable->morning_shift;
    }

    function getAfternoonShift() {
        return $this->fillable->afternoon_shift;
    }

}
