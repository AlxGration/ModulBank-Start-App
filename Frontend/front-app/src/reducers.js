import {combineReducers} from 'redux';
import accounts from './modules/accounts/reducers/accountsReducer';
import signUp from './modules/registration/reducers/signUpReducer';
import signIn from './modules/login/reducers/signInReducer';
import app from './modules/app/reducers/appReducer'

const rootReducer = combineReducers({
    app,
    accounts,
    signIn,
    signUp,
    
});

export default rootReducer;