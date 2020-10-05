import '../enzyme.config.js';
import React from 'react';
import {shallow} from 'enzyme';

import Header from '../Components/Margins/Header';

function testInitialState(){
    const app = shallow(<Header />);
    let expectedState = {
    };
    expect(app.state()).toEqual(expectedState);
}

test("Testing header initial state", testInitialState);