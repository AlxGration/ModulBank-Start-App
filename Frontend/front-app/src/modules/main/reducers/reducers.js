import {combineReducers} from 'redux';
import accounts from '../../accounts/reducers/accountsReducer';
import signUp from '../../auth/reducers/signUpReducer';
import signIn from '../../auth/reducers/signInReducer';
import app from './appReducer'

const rootReducer = combineReducers({
    app,
    accounts,
    signIn,
    signUp,
    
});

export default rootReducer;