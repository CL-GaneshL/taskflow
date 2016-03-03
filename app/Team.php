<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Team extends Model {

    protected $fillable = [
        'id',
        'teamName',
        'teamLeaderId'
    ];
    public $timestamps = false;

    function getId() {
        return $this->fillable->id;
    }

    function getTeamName() {
        return $this->fillable->teamName;
    }

    function getTeamLeaderId() {
        return $this->fillable->teamLeaderId;
    }

}
