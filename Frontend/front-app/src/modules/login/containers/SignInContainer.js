import React from 'react';
import {Link, Redirect} from 'react-router-dom';
import {connect} from 'react-redux';
import {postSignInRequest, postSignInError} from '../actions//authenticationActions';
import '../../../App.css';


class SignInContainer extends React.Component  {

    onSubmit = (event) => {
        event.preventDefault();
        
        let email = event.target.email.value;
        let password = event.target.password.value;

        const data = {
            email, password
        };

        if ( ! this.validateEmail(email)){
            this.props.postSignInError({errorMessage:'Некорректный email'});
            return;
        }

        if(password.length < 6){
            this.props.postSignInError({errorMessage:'Слишком короткий пароль(<6)'});
            return;
        }

        this.props.postSignInRequest(data);
    }

    validateEmail = (mail) => {
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)){
            return (true);
        }
        return (false);
    }

    
    render(){

        const{ 
            authorized, 
            errorMessage, 
            loading} = this.props;
    
        if (authorized){
            return(<Redirect to="/private" />);
        }

        if (loading){
            return (<div>Loading...</div>);
        }
        
        return (
            <div className='center-block'>
                <h1>Авторизация</h1>

                { errorMessage ?  <div className="error-message">{errorMessage}</div> :<div/>}
                
                <form onSubmit={this.onSubmit}>
                    <input type='text' 
                        id='email'
                        placeholder='Email' 
                    />
                    
                    <br/>
                    <br/>

                    <input type='password'
                        id='password'
                        placeholder='Пароль'
                    />
                    <br/>
                    <button className="small-button">Войти</button>
                </form>
            
                <br/>
                <Link to='/signup'>
                    Регистрация
                </Link>
            </div>
        );
    }
};

const mapStateToProps = (state) => ({
    loading: state.signIn.loading,
    errorMessage: state.signIn.errorMessage,
});

const mapDispatchProps = {
    postSignInRequest,
    postSignInError,
}

export default connect(mapStateToProps, mapDispatchProps)(SignInContainer);
