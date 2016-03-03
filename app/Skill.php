<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Skill extends Model {

    protected $fillable = ['id', 'reference', 'designation', 'duration'];
    public $timestamps = false;

    function getId() {
        return $this->fillable->id;
    }

    function getReference() {
        return $this->fillable->reference;
    }

    function getDesignation() {
        return $this->fillable->designation;
    }

}
