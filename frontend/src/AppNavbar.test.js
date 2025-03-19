import AppNavbar from "./AppNavbar";
import { render, screen, fireEvent } from "./test-utils";
import tokenService from './services/token.service';
import { MemoryRouter } from 'react-router-dom';



describe('AppNavbar', () => {

    test('renders public links correctly', () => {
        render(<AppNavbar />);
        const linkDocsElement = screen.getByRole('link', { name: 'Docs' });
        expect(linkDocsElement).toBeInTheDocument();

        const linkPlansElement = screen.getByRole('link', { name: 'Pricing Plans' });
        expect(linkPlansElement).toBeInTheDocument();
    });

    test('renders not user links correctly', () => {
        render(<AppNavbar />);
        const linkDocsElement = screen.getByRole('link', { name: 'Register' });
        expect(linkDocsElement).toBeInTheDocument();

        const linkPlansElement = screen.getByRole('link', { name: 'Login' });
        expect(linkPlansElement).toBeInTheDocument();
    });


});
