import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Header from './Header';
import tokenService from '../services/token.service';

jest.mock('../services/token.service');

describe('Header', () => {
    it('should call removeUser and navigate to /login on logout', () => {
        const mockRemoveUser = jest.fn();
        tokenService.removeUser = mockRemoveUser;

        const { container } = render(
            <MemoryRouter>
                <Header />
            </MemoryRouter>
        );

        const logoutButton = screen.getByText('Logout');
        fireEvent.click(logoutButton);

        expect(mockRemoveUser).toHaveBeenCalled();
        expect(container.innerHTML).toMatch('Login');
    });
});