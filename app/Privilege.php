<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Privilege extends Model {

    protected $fillable = ['id', 'label', 'designation'];
    public $timestamps = false;

    function getId() {
        return $this->fillable->id;
    }

    function getLabel() {
        return $this->fillable->label;
    }

    function getDesignation() {
        return $this->fillable->designation;
    }

}
