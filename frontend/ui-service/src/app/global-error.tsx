"use client"

type Props = Error & { digest?: string; reset: () => void }

export default function GlobalError({ message, digest, reset }: Props) {
  return (
    <html>
      <body>
        <div>
          <div>
            <h2>{digest}</h2>
          </div>
          <div>
            <h3>{message}</h3>
          </div>
        </div>
      </body>
    </html>
  )
}
