<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Login extends CI_Controller{
    
    
    //Prikazi login view
    function index(){
      $data['main_content'] = 'login_view';
      $this->load->view('includes/template',$data);
    }
    
    
    //Obradi podatke poslate kroz formu
    function validate(){
     $this->load->model('membership_model');
     $query = $this->membership_model->validate();
     
     //Ako je zahtev Ajax
     if($this->input->post('ajax')==1){
         if($query==true) echo "1";
         else echo "0";
         die();
     }
     
     //Ako user postoji kreiraj sesiju
     if($query){
         $data = array(
         'username' => $this->input->post('username'),
         'is_logged_in' => true
         );
         
       $this->session->set_userdata($data);
       redirect(''); 
     }
     
     //Ako user ne postoji prikazi:
     else {
         $this->index();    
     }
     
     
    }//validate();
    
    
    
    
    
    
}//class
