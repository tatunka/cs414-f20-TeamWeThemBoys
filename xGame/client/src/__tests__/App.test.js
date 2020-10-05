import '../enzyme.config';
import React from 'react';
import {shallow} from 'enzyme';
import App from '../Components/App';

function testInitialState(){
    const app = shallow(<App />);
    let expectedState = {
        showLogin: false,
        showProfile: false,
        showMatch: false
    };
    expect(app.state()).toEqual(expectedState);
}

test("Testing app initial state", testInitialState);