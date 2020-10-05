import '../enzyme.config.js';
import React from 'react';
import {shallow} from 'enzyme';

import Login from '../Components/Pages/Login';

function testInitialState(){
    const app = shallow(<Login />);
    let expectedState = {
    };
    expect(app.state()).toEqual(expectedState);
}

test("Testing login initial state", testInitialState);