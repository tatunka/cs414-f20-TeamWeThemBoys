import '../enzyme.config.js';
import React from 'react';
import {shallow} from 'enzyme';

import Profile from '../Components/Pages/Profile';

function testInitialState(){
    const app = shallow(<Profile />);
    expect(app.html()).toMatchSnapshot();
}

test("Testing profile initial state", testInitialState);