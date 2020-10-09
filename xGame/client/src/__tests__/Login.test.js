import '../enzyme.config.js';
import React from 'react';
import {shallow} from 'enzyme';

import Login from '../Components/Pages/Login';

function testInitialState(){
    const app = shallow(<Login />);
    expect(app.html()).toMatchSnapshot();
}

test("Testing login initial state", testInitialState);