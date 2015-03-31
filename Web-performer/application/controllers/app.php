<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App extends CI_Controller{
    
    function __construct(){
    parent::__construct();
    $this->is_logged_in();    
    }
    
    //@TODO Uspesno se ulogovao,sta dalje..
    function index(){
      $data['main_content'] = 'app_view';
      $this->load->view('includes/template',$data);
    }
    
    
    function is_logged_in(){
        $is_logged_id = $this->session->userdata('is_logged_in');
        if(!isset($is_logged_id) || $is_logged_id!==true){
            redirect('login');
        } 
    }
    
}//class
