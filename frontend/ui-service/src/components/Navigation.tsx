"use client"

import { BrandLogo } from "@/components/BrandLogo"
import {
  BreadcrumbItem,
  Breadcrumbs,
  Button,
  Link,
  Navbar,
  NavbarBrand,
  NavbarContent,
  NavbarItem,
  NavbarMenu,
  NavbarMenuItem,
  NavbarMenuToggle,
} from "@nextui-org/react"
import { useState } from "react"

const menuItems = ["내정보", "홈화면", "수입내역", "수입통계", "지출내역", "지출통계", "지출계획", "로그아웃"]

export default function Navigation() {
  const [isMenuOpen, setIsMenuOpen] = useState(false)

  return (
    <div className="mb-8">
      <Navbar onMenuOpenChange={setIsMenuOpen} isBordered>
        <NavbarContent>
          <NavbarMenuToggle aria-label={isMenuOpen ? "Close menu" : "Open menu"} className="sm:hidden" />
          <NavbarBrand>
            <BrandLogo />
            <p className="font-bold text-inherit">FM</p>
          </NavbarBrand>
        </NavbarContent>

        <NavbarContent className="hidden sm:flex gap-4" justify="center">
          <NavbarItem>
            <Link color="foreground" href="#">
              수입
            </Link>
          </NavbarItem>
          <NavbarItem isActive>
            <Link href="#" aria-current="page">
              지출
            </Link>
          </NavbarItem>
          <NavbarItem>
            <Link color="foreground" href="#">
              통계
            </Link>
          </NavbarItem>
        </NavbarContent>

        <NavbarContent justify="end">
          <NavbarItem className="hidden lg:flex">
            <Link href="#">로그인</Link>
          </NavbarItem>
          <NavbarItem>
            <Button as={Link} color="primary" href="#" variant="flat">
              회원 가입
            </Button>
          </NavbarItem>
        </NavbarContent>

        <NavbarMenu>
          {menuItems.map((item, index) => (
            <NavbarMenuItem key={`${item}-${index}`}>
              <Link color={index === 2 ? "primary" : index === menuItems.length - 1 ? "danger" : "foreground"} className="w-full" href="#" size="lg">
                {item}
              </Link>
            </NavbarMenuItem>
          ))}
        </NavbarMenu>
      </Navbar>
      <div className="flex flex-col flex-wrap mt-4 items-center">
        <Breadcrumbs size="sm">
          <BreadcrumbItem>Home</BreadcrumbItem>
          <BreadcrumbItem>Music</BreadcrumbItem>
          <BreadcrumbItem>Artist</BreadcrumbItem>
          <BreadcrumbItem>Album</BreadcrumbItem>
          <BreadcrumbItem>Song</BreadcrumbItem>
        </Breadcrumbs>
      </div>
    </div>
  )
}
