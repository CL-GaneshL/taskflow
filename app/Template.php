<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Template extends Model {

    // ----------------------------------
    // - table 
    // ----------------------------------
    protected $table = 'project_templates';
    // ----------------------------------
    // - fillable attributs
    // ----------------------------------
    protected $fillable = ['id', 'reference', 'designation'];
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

    function getReference() {
        return $this->fillable->reference;
    }

    function getDesignation() {
        return $this->fillable->designation;
    }

}
