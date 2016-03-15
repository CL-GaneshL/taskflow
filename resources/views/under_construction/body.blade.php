
@extends('layouts.under_construction_master')

@section('body')

        <!-- preloader start -->
        <div class="preloader" id="preloader">
            <div class="item">
                <div class="loader">
                </div>
            </div>
        </div>
        <!-- preloader end -->


        <!-- Microsoft custom tile -->
        <div id="TileOverlay" style='background-color: Highlight; height: 100%; width: 100%; top: 0px; left: 0px; position: fixed; color: black'>
            <img src="{{ asset('img/icons/nugget-768.png') }}" width="320" height="320" />
            <div style='margin-top: 40px'>
                Pin this site to your start screen using the menu… 
            </div>
        </div>

        <!-- Microsoft custom tile -->
        <a id="LinkOverlay" >Pin this site to your start screen</a>

        <div class="intro clearfix" id="fullpage">

            <!-- section 0 -->
            <div class="section video-intro" id="section-video">
                <div class="video-container">
                    <div class="opacity"></div><!-- opacity -->
                    <div class="content">
                        <div class="container">
                            <div class="row">
                                <!-- logo -->
                                <!--div class="logo-header">	
                                    <img src="img/logo.png" alt=""/>
                                </div-->


                                <h1 class="headline">NuggetBox.gold is under construction.</h1>
                                <div id="counter"></div>

                            </div>
                            <div class="row">
                                <div class="col-md-10 col-md-offset-1">
                                    <h3>Please visit us later. You can also subscribe to get the latest updates on the progress.</h3>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-8 col-md-offset-2">
                                    <div class="newsletter">
                                        <form class="subscriptionForm" method="post" id="mc-form">
                                            <input type="email" name="EMAIL" class="email" id="mce-EMAIL" placeholder="Enter your email address" required>
                                            <input type="submit" id="subscribe-btn" value="Subscribe">
                                            <label for="mce-EMAIL" class="subscribe-message"></label>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <video autoplay loop class="fillWidth">
                        <source src="{{ asset('video/under_construction/market-trends.mp4') }}" type="video/mp4">
                        <source src="{{ asset('video/under_construction/market-trends.webm') }}" type="video/webm">
                        <source src="{{ asset('video/under_construction/market-trends.ogv') }}" type="video/ogg">                       
                    </video>
                    <div class="poster hidden">
                        <img src="{{ asset('img/under_construction/market-trends.jpg') }}" alt="">
                    </div>
                </div>
                <!--div class="arrow-down"></div-->
            </div>


        </div>


@stop



