import {put, takeEvery} from 'redux-saga/effects';
import {LOGIN_SAVE_REQUEST, LOGOUT_REQUEST, loginSaveSuccess, logOutSuccess} from '../actions/appActions';

function* logIn(payload) {
 
    // сохранение токена и айди
    console.log('save auth '+ JSON.stringify(payload.data));

    localStorage.setItem('token', payload.data.token);
    localStorage.setItem('userId', payload.data.userId);
    localStorage.setItem('expiration', payload.data.expiration);

    yield put(loginSaveSuccess());
}
export function* saveAuthSagas(payload){
    yield takeEvery(LOGIN_SAVE_REQUEST, logIn);
}
//------------------------------------------------------------------------
//
//------------------------------------------------------------------------
function* logOut() {
 
    // удаление токена и айди
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('expiration');

    yield put(logOutSuccess());
}
export function* removeAuthSagas(){
    yield takeEvery(LOGOUT_REQUEST, logOut);
}