import '../enzyme.config';
import React from 'react';
import {shallow} from 'enzyme';

import App from '../Components/App';

function testInitialState(){
    const app = shallow(<App />);
    expect(app.html).toMatchSnapshot();
}

test("Testing app initial state", testInitialState);