<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Project extends Model {

    // ----------------------------------
    // - fillable attributs
    // ----------------------------------
    protected $fillable = ['id', 'reference', 'template_id', 'nb_products', 'priority', 'start_date'];
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

    function getTemplateId() {
        return $this->fillable->template_id;
    }

    function getPriority() {
        return $this->fillable->priority;
    }

    function getStartDate() {
        return $this->fillable->start_date;
    }

}
