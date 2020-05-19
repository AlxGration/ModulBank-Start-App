import {put, call, takeEvery} from 'redux-saga/effects';
import {GET_ACCOUNTS_REQUEST, getAccountsSuccess, getAccountsError} from '../actions/accountsActions';
import {fetchAccounts} from '../api/accountsApi';

export function* getAccounts(){
    try{

        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('userId');
        const data = {
            token, 
            userId
        };

        const response = yield call(fetchAccounts, data);
        if (response.errorMessage){
            yield put(getAccountsError(response));
        }else{
            yield put(getAccountsSuccess(response));
        }
        
    }catch(error){
        yield put(getAccountsError(error.message));
    }
}

function* accountsSagas(){
    yield takeEvery(GET_ACCOUNTS_REQUEST, getAccounts);
}

export { accountsSagas };