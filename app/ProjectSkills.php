<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ProjectSkills extends Model {

    // ----------------------------------
    // - fillable attributs
    // ----------------------------------
    protected $fillable = [
        'id',
        'project_id',
        'duration'
    ];
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

    function getProjectId() {
        return $this->fillable->project_id;
    }

    function getDuration() {
        return $this->fillable->duration;
    }

}
